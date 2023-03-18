package com.alexisboiz.covoitapp.carpool_area;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

public interface CarpoolAreaDataCallback {
    void getResponseSuccess(CarpoolAreaData carpoolAreaData);
    void getResponseError(String errorText);
}
