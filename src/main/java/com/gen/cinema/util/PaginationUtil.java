package com.gen.cinema.util;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.gen.cinema.dto.response.ResultPageResponseDTO;



public class PaginationUtil {
    public static <T> ResultPageResponseDTO<T> createResultPageDTO(List<T> dtos, Long totalElements, Integer pages){
		ResultPageResponseDTO<T> result = new ResultPageResponseDTO<T>();
		result.setPages(pages);
		result.setElements(totalElements);
		result.setResults(dtos);
		return result;
	}
	
	public static Sort.Direction getSortBy(String sortBy){
		if(sortBy.equalsIgnoreCase("asc")) {
			return Sort.Direction.ASC;
		}else {
			return Sort.Direction.DESC;
		}
	}

	public static Direction getDirection(String direction){
		if(direction.equalsIgnoreCase("asc")) {
			return Direction.ASC;
		}else {
			return Direction.DESC;
		}
	}

}
