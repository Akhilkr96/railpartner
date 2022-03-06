/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.partner.railpartner.booking.web;

import java.util.Optional;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.partner.railpartner.booking.Book;
import com.partner.railpartner.booking.Bookings;

import lombok.RequiredArgsConstructor;

/**
 * @author Oliver Drotbohm
 */

@RequiredArgsConstructor
@BasePathAwareController
//@RestController
public class BookController {

	private final Bookings bookings;

	/**
	 * Custom handler method to customize the creation of {@link Order}s.
	 *
	 * @param payload
	 * @param errors
	 * @param assembler
	 * @return
	 */
	@PostMapping(path = "/booking/Change")
	public HttpEntity<?> placeOrder(@RequestBody Book book, Errors errors,
			PersistentEntityResourceAssembler assembler) {

		Book b = bookings.bookChange(book);
		
		 PersistentEntityResource it = assembler.toFullResource(b);
		ResponseEntity<PersistentEntityResource> entity = ResponseEntity.created(it.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(it);
	
		
		return entity;
		/*
		 * return MappedPayloads.of(payload, errors)
		 * .mapIfValid(LocationAndDrinks::toOrder) .mapIfValid(orders::save)
		 * .mapIfValid(assembler::toFullResource) .concludeIfValid(it ->
		 * ResponseEntity.created(it.getRequiredLink(IanaLinkRelations.SELF).toUri()).
		 * body(it));
		 */	}
	
	
	
	@GetMapping(path = "/bookingseat")
	public HttpEntity<?> bookSeats() {

		Book b = bookings.book();
		return new HttpEntity<Book>(b);
		/*
		 * return MappedPayloads.of(payload, errors)
		 * .mapIfValid(LocationAndDrinks::toOrder) .mapIfValid(orders::save)
		 * .mapIfValid(assembler::toFullResource) .concludeIfValid(it ->
		 * ResponseEntity.created(it.getRequiredLink(IanaLinkRelations.SELF).toUri()).
		 * body(it));
		 */	}
	
	
	
	
	@GetMapping(path = "/bookings")
	public HttpEntity<?> allbookings() {

		Iterable<Book> b = bookings.allBookins();
			return new HttpEntity<Iterable<Book>>(b);
	}
	
	
	@GetMapping(path = "/books/{id}")
	public HttpEntity<?> getBooking(@PathVariable("id")String bookingId) {

		
		Optional<Book> b = bookings.findById(Long.parseLong(bookingId));
		
		
		
		HttpEntity<?> entity=new HttpEntity<Book>(b.get());
		return entity;
	}
}
