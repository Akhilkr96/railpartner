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
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import com.partner.railpartner.core.AbstractEntity;

@Entity
@Getter
@Setter
@ToString(exclude = "items")
@Table(name = "RBOrder")
public class Book extends AbstractEntity {

	private final LocalDateTime bookingDate;

	private Status status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//
	private Set<Item> items = new HashSet<Item>();

	/**
	 * Creates a new {@link Book} for the given {@link Item}s and {@link Location}.
	 * 
	 * @param items must not be {@literal null}.
	 * @param location
	 */
	public Book(Collection<Item> items) {

		this.status = Status.PAYMENT_EXPECTED;
		this.items.addAll(items);
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

	

	/**
	 * Marks the {@link Book} as payed.
	 */
	public void markPaid() {

		if (isPaid()) {
			throw new IllegalStateException("Already paid order cannot be paid again!");
		}

		this.status = Status.PAID;
	}

	/**
	 * Marks the {@link Book} as in preparation.
	 */
	public void markInPreparation() {

		if (this.status != Status.PAID) {
			throw new IllegalStateException(String.format("Order must be in state payed to start preparation! "
					+ "Current status: %s", this.status));
		}

		this.status = Status.PREPARING;
	}

	/**
	 * Marks the {@link Book} as prepared.
	 */
	public void markPrepared() {

		if (this.status != Status.PREPARING) {
			throw new IllegalStateException(String.format("Cannot mark Order prepared that is currently not "
					+ "preparing! Current status: %s.", this.status));
		}

		this.status = Status.READY;
	}

	/**
	 * Returns whether the {@link Book} has been paid already.
	 * 
	 * @return
	 */
	public boolean isPaid() {
		return !this.status.equals(Status.PAYMENT_EXPECTED);
	}

	/**
	 * Returns if the {@link Book} is ready to be taken.
	 * 
	 * @return
	 */
	public boolean isReady() {
		return this.status.equals(Status.READY);
	}

	public boolean isTaken() {
		return this.status.equals(Status.TAKEN);
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
		PAYMENT_EXPECTED,

		/**
		 * {@link Order} was payed. No changes allowed to it anymore.
		 */
		PAID,

		/**
		 * The {@link Order} is currently processed.
		 */
		PREPARING,

		/**
		 * The {@link Order} is ready to be picked up by the customer.
		 */
		READY,

		/**
		 * The {@link Order} was completed.
		 */
		TAKEN;
	}
}
