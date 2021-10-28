package com.boa.foodyapi.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;

@Entity
@Table(name="Food_Order")
@Data
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Order_Id")
	private long orderId;
	@Column(name="Item_Id")
	private long itemId;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy-MM-dd")
	@JsonDeserialize(using=LocalDateDeserializer.class)
	@JsonSerialize(using=LocalDateSerializer.class)
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="Date_Of_Order")
	private LocalDate dor;
	@Column(name="Time_Of_Order")
	private String time;
	@Column(name="Qty")
	private int qty;
	
	
}
