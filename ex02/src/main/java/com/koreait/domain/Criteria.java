package com.koreait.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;

//Criteria : 검색의 기준
@Data
@AllArgsConstructor
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum  = pageNum;
		this.amount = amount;
	}
	
	
	//typeArr이라는 인스턴스 변수가 있는 것처럼 getter를 선언해준다
	//파라미터로 문자열 배열인 typeArr을 전달받지 않기 때문에 getter만 따로 선언하여 동적쿼리에서 typeArr로 접근하여 리턴값을 사용한다.
	public String[] getTypeArr() {
		//split("") : 구분점 없이 각각의 문자를 분리 "ABC".split("")[1] == "B"
		return type == null ? new String [] {} : type.split("");
	}
	
	//쿼리 스트링을 만들어줌
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum",this.pageNum)
				.queryParam("amount",this.amount)
				.queryParam("type",this.type)
				.queryParam("keyword",this.keyword);
		
		return builder.toUriString();
				
	}
}







