package com.example.festo.mock;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MockDataRequest {

    private List<MockBoothRequest> mockBoothRequestList;
}
