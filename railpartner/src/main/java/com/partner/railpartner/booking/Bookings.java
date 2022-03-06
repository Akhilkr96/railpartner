/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.partner.railpartner.booking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.partner.railpartner.core.AbstractEntity;

/**
 * Repository to manage {@link Book} instances.
 *
 * @author Oliver Gierke
 */
@RepositoryRestResource
public interface Bookings
		extends PagingAndSortingRepository<Book, AbstractEntity> {

	/**
	 * Returns all {@link Book}s with the given {@link Status}.
	 *
	 * @param status must not be {@literal null}.
	 * @return
	 */
	List<Book> findByStatus(@Param("status") Book.Status status);

	/**
	 * Marks the given {@link Book} as paid.
	 *
	 * @param Book must not be {@literal null}.
	 * @return
	 */
	@Transactional
	default Book book() {
		return save(new Book());
	}

	@Transactional
	default Book bookChange(Book b) {
		return save(b);
	}
	

	default Iterable<Book> allBookins(){
		return findAll();
	}

	//List<Book> findAll();
	


 Optional<Book> findById(Long id) ;	
}
