package org.citi.chatgpt.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class HolidayResponse {
    String statusMsg;
    Set<String> yes;
    Set<String> no;
}
