package com.macaplix.squaregames.dto;

import java.util.Date;

public record StatsSummaryDTO(int playerID, Date startDate, Date endDate, int gamecount, double averageScore) {
}
