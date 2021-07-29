package com.mococo.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@Entity(name="material")
@IdClass(MaterialPK.class)
public class Material implements Serializable {
	@Id
	@Column(name = "recipe_number")
	private int recipeNumber;
	@Id
	@Column(name = "crop_number")
	private int cropNumber;
}
