/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.core.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Add a TextWatcher listener to this TextView using the provided actions.
 */
inline fun TextView.addTextChangedListener(
    crossinline afterChanged: (Editable) -> Unit = {},
    crossinline beforeChanged: (CharSequence, Int, Int, Int) -> Unit = { _, _, _, _ -> },
    crossinline onChanged: (CharSequence, Int, Int, Int) -> Unit = { _, _, _, _ -> }
): TextWatcher = object : TextWatcher {
    override fun afterTextChanged(s: Editable) {
        afterChanged(s)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        beforeChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        onChanged(s, start, before, count)
    }
}.also { addTextChangedListener(it) }

/**
 * Do [action] after the text has changed.
 */
inline fun TextView.doAfterTextChanged(crossinline action: (Editable) -> Unit) =
    addTextChangedListener(afterChanged = action)

/**
 * Do [action] before the text change.
 */
inline fun TextView.doBeforeTextChanged(crossinline action: (CharSequence, Int, Int, Int) -> Unit) =
    addTextChangedListener(beforeChanged = action)

/**
 * Do [action] while the text is changing.
 */
inline fun TextView.doOnTextChanged(crossinline action: (CharSequence, Int, Int, Int) -> Unit) =
    addTextChangedListener(onChanged = action)