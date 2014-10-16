/*
 * Copyright 2014 Michael Greifeneder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mikegr.iban4android;

import android.annotation.SuppressLint;
import android.content.Context;

import org.iban4j.BicFormatException;
import com.github.mikegr.iban4android.R;

import static org.iban4j.BicFormatException.*;
import static org.iban4j.BicFormatException.Constraint.*;

public class BicFormatExceptionFormatter extends AndroidExceptionFormatter {


    public BicFormatExceptionFormatter(Context ctx) {
        super(ctx);
    }

    @SuppressLint("StringFormatMatches")
    public String getErrorMessage(BicFormatException ex) {
        Object[] values = ex.getValues();
        switch(ex.getConstraint()) {
            case length: return ctx.getString(R.string.iban4j_error_msg_bic_length, values);
            case bank_code_only_letters: return ctx.getString(R.string.iban4j_error_msg_bic_bank_code_only_letters);
            case country_only_upper_case: return ctx.getString(R.string.iban4j_error_msg_bic_country_only_upper_case);
            case non_existing_country: return ctx.getString(R.string.iban4j_error_msg_bic_non_existing_country, values);

            case location_code_only_letters_or_digits: return ctx.getString(R.string.iban4j_error_msg_bic_location_code_only_letters_or_digits);
            case branch_code_only_letters_or_digits: return ctx.getString(R.string.iban4j_error_msg_bic_branch_code_only_letters_or_digits);
            case is_null: ctx.getString(R.string.iban4j_error_msg_bic_null);
            case bic_upper_case: return ctx.getString(R.string.iban4j_error_msg_bic_upper_case);
        }
        return ex.getMessage();
    }


}
