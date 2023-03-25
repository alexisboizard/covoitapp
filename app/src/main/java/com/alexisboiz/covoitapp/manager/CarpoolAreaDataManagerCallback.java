package com.alexisboiz.covoitapp.manager;

import com.alexisboiz.covoitapp.model.CarpoolAreaData;

public interface CarpoolAreaDataManagerCallback {
    void getCarpoolAreaResponseSuccess(CarpoolAreaData data);
    void getCarpoolAreaResponseError(String error);
}
