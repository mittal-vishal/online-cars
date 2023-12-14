package com.intuit.craft.onlinecars.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Table(name = "similarity_score")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class SimilarityScore implements Serializable {
    @Id
    private String id;
    @Type(type = "jsonb")
    @Column(columnDefinition = "json", nullable = false)
    private Map<String, Double> data;

}
