package com.raghunathit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name="AIT_STUDENT_ENQURIES")
@Data
public class StudentEnqEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	private String name;
	private Long phno;
	private String classMode;
	private String courseName;
	private String enqStatus;
	@CreationTimestamp
	private LocalDate createdDate;
	@CreationTimestamp
	private LocalDate updatedDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDtlsEntity user; 
	
	
	 


}
