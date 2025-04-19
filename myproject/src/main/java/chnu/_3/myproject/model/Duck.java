package chnu._3.myproject.model;/*
  @author Bogdan
  @project myproject
  @class Duck
  @version 1.0.0
  @since 19.04.2025 - 19.22
*/

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document

public class Duck {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    public Duck(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duck duck = (Duck) o;
        return Objects.equals(id, duck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
