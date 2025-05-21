package com.anthonini.gainex.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name = "street_address")
	private String streetAddress;
	
	private String number;
	
	private String neighborhood;
	
	@Column(name = "address_line_2")
	private String addressLine2;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	private String city;
	
	@Column(name = "state_or_region")
	private String stateOrRegion;

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateOrRegion() {
		return stateOrRegion;
	}

	public void setStateOrRegion(String stateOrRegion) {
		this.stateOrRegion = stateOrRegion;
	}
}
