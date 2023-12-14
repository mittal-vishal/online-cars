package com.intuit.craft.onlinecars.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComparisonRequest {
    @Size(min = 2, max = 3, message = "Number of ID's should be between 2 and 3")
    List<String> ids;
}
