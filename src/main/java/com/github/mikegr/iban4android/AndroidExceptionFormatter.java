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

import android.content.Context;

import org.iban4j.BicFormatException;
import org.iban4j.Iban4jException;
import org.iban4j.IbanFormatException;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import com.github.mikegr.iban4android.R;

public class AndroidExceptionFormatter {

    protected Context ctx;

    public AndroidExceptionFormatter (Context ctx) {
        this.ctx = ctx;
    }


    public static CharSequence getMessage(Context ctx, Iban4jException ex) {
        if (ex instanceof  IbanFormatException) {
            return new IbanFormatExceptionFormatter(ctx).getMessage((IbanFormatException) ex);
        }
        if (ex instanceof  BicFormatException) {
            return new BicFormatExceptionFormatter(ctx).getErrorMessage((BicFormatException) ex);
        }
        if (ex instanceof  InvalidCheckDigitException) {
            return new InvalidCheckDigitExceptionFormatter(ctx).getMessage((InvalidCheckDigitException) ex);
        }
        if (ex instanceof  UnsupportedCountryException) {
            return new UnsupportedCountryExceptionFormatter(ctx).getMessage((UnsupportedCountryException) ex);
        }
        return ex.getMessage();
    }
}
