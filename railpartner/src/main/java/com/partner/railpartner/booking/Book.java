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



import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.partner.railpartner.core.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "items")
@Table(name = "RailBook")
public class Book extends AbstractEntity {

	private final LocalDateTime bookingDate;

	private Status status;

	
	//@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//
	

	/**
	 * Creates a new {@link Book} for the given {@link Item}s and {@link Location}.
	 * 
	 * @param items must not be {@literal null}.
	 * @param location
	 */
	public Book(Collection<Item> items) {

		this.status = Status.BOOKED;
		//ßthis.items.addAll(items);
		this.bookingDate = LocalDateTime.now();
		
	}

	/**
	 * Creates a new {@link Book} containing the given {@link Item}s.
	 * 
	 * @param items must not be {@literal null}.
	 */
	public Book(Item... items) {
		this(Arrays.asList(items));
	}

	public Book() {
		this(new Item[0]);
	}

	public Book(Long id) {
		super(id);
		this.bookingDate = null;
	}
	

	/**
	 * Marks the {@link Book} as payed.
	 */
	public void markPaid() {

		if (isPaid()) {
			throw new IllegalStateException("Already paid  cannot be paid again!");
		}

		this.status = Status.PAID;
	}

	
	/**
	 * Marks the {@link Book} as payed.
	 */
	public void markBooked() {

		if (isPaid()) {
			throw new IllegalStateException("Already booked cannot be booked again!");
		}

		this.status = Status.PAID;
	}
	
	/**
	 * Returns whether the {@link Book} has been paid already.
	 * 
	 * @return
	 */
	public boolean isPaid() {
		return !this.status.equals(Status.PAID);
	}
	
	
	/**
	 * Returns whether the {@link Book} has been paid already.
	 * 
	 * @return
	 */
	public boolean isBooked() {
		return !this.status.equals(Status.BOOKED);
	}

	/**
	 * Enumeration for all the statuses an {@link Book} can be in.
	 * 
	 * @author Akhil
	 */
	public static enum Status {

		/**
		 * Placed, but not payed yet. Still changeable.
		 */
		BOOKED,

		/**
		 * {@link Booking} was payed. No changes allowed to it anymore.
		 */
		PAID
		
	}
}
