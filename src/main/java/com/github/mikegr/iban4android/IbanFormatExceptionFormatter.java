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

import org.iban4j.IbanFormatException;
import com.github.mikegr.iban4android.R;

import static org.iban4j.IbanFormatException.*;
import static org.iban4j.IbanFormatException.Constraint.*;
import static org.iban4j.IbanFormatException.Constraint.invalid_length;

public class IbanFormatExceptionFormatter extends AndroidExceptionFormatter {


    public IbanFormatExceptionFormatter(Context ctx) {
        super(ctx);
    }

    @SuppressLint("StringFormatMatches")
    public String getMessage(IbanFormatException ex) {
        Object[] values = ex.getValues();
        switch(ex.getConstraint()) {

            case invalid_length: return ctx.getString(R.string.error_msg_invalid_length, values);
            case invalid_character: return ctx.getString(R.string.error_msg_invalid_character, values);
            case is_null: return ctx.getString(R.string.error_msg_null_iban);
            case assert_msg_digits: return ctx.getString(R.string.assert_msg_digits, values);
            case assert_msg_digits_and_letters: return ctx.getString(R.string.assert_msg_digits_and_letters, values);
            case assert_msg_upper_letters: return ctx.getString(R.string.assert_msg_upper_letters, values);
            case bank_code_required: return ctx.getString(R.string.error_msg_iban_bank_code_required);
            case account_number_required: return ctx.getString(R.string.error_msg_iban_account_number_required);
            case checksum_only_numeric: return ctx.getString(R.string.error_msg_iban_checksum_only_numeric);
            case pos_uppercase_only: return ctx.getString(R.string.error_msg_bban_pos_uppercase_only, values);
            case pos_numeric_only: return ctx.getString(R.string.error_msg_bban_pos_numeric_only, values);
            case pos_alphanumeric_only: return ctx.getString(R.string.error_msg_bban_pos_alphanumeric_only, values);
        }
        return ex.getMessage();
    }
}
