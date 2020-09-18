package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Bookmark;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IBookmarkRepository;
import com.ecommerce.infrastructure.repository.factory.BookmarkFactory;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import com.ecommerce.infrastructure.repository.factory.RatingFactory;

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
public class BookmarkRepository implements IBookmarkRepository {
	private static final Logger log = LoggerFactory.getLogger(BookmarkRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Bookmark> list() {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM bookmarks");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] {},
					(rs, rowNum) -> BookmarkFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Bookmark bookmark) {
		try {
			log.debug(bookmark.toString());
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("user_id", bookmark.getUserId());
			paramMap.put("item_id", bookmark.getItemId());

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("bookmarks")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(final long id) {
		StringBuilder sbSql = new StringBuilder("DELETE FROM bookmarks ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

}
