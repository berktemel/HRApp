package ems.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String to;
    private String subject;
    private String body;

    @Override
    public String toString() {
        return "{\n\tto: " + this.to +
                "\n\tsubject: " + this.subject +
                "\n\tbody: " + this.body +
                "\n}";
    }
}
