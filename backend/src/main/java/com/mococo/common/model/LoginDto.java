package com.mococo.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginDto {

	
	@ApiModelProperty(example = "admin")
	private String id;
	@ApiModelProperty(example = "admin")
	private String password;

	private String token;

}
