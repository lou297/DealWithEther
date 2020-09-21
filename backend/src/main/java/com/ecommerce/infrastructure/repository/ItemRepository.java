package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository implements IItemRepository {
	private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Item> list() {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE available=?"); // where available
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { true },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> pageList(final int page) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE available=1 limit 5 offset ?"); // where
																											// available
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { (page - 1) * 5 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByUser(final long userId) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE seller=? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { userId },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByName(final String name, final int page) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE name like ? limit 5 offset ?");
		String tname = "%" + name + "%";
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { tname, (page - 1) * 5 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByCategory(final String category, final int page) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE category=? limit 5 offset ?");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { category, (page - 1) * 5 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Item get(final long id) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(), new Object[] { id },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Item item) {
		try {
			log.debug(item.toString());
			ZonedDateTime utc = ZonedDateTime.now(ZoneId.of("UTC"));
			LocalDateTime now = utc.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("name", item.getName());
			paramMap.put("category", item.getCategory());
			paramMap.put("explanation", item.getExplanation());
			paramMap.put("available", true);
			paramMap.put("seller", item.getSeller());
			paramMap.put("registered_at", now);
			paramMap.put("image", item.getImage());
			paramMap.put("price", item.getPrice());
			paramMap.put("direct_deal", item.isDirectDeal());
			if (item.isDirectDeal()) {
				paramMap.put("deal_region", item.getDealRegion());
			}

			log.debug("registered_at: " + paramMap.get("registered_at"));

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("items")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int update(final Item item) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET name=?, category=?, explanation=?, available=?, image=?, price=?, direct_deal=? ");
		if (item.isDirectDeal()) {
			sbSql.append(", deal_region=? ");
		}
		sbSql.append("where id=?");
		try {
			if (item.isDirectDeal()) {
				return this.jdbcTemplate.update(sbSql.toString(),
						new Object[] { item.getName(), item.getCategory(), item.getExplanation(), item.getAvailable(),
								item.getImage(), item.getPrice(), item.isDirectDeal(), item.getDealRegion(),
								item.getId() });
			} else {
				return this.jdbcTemplate.update(sbSql.toString(),
						new Object[] { item.getName(), item.getCategory(), item.getExplanation(), item.getAvailable(),
								item.getImage(), item.getPrice(), item.isDirectDeal(), item.getId() });
			}
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int imageUpdate(final long id, final int image) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET image=? ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { image, id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(final long id) {
		StringBuilder sbSql = new StringBuilder("DELETE FROM items ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

}
