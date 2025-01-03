// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.injection.qualifiers.AppForeground;
import com.google.firebase.inappmessaging.model.RateLimit;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;

/**
 * Bindings for rate limits
 *
 * @hide
 */
@Module
public class RateLimitModule {
  private static final String APP_FOREGROUND_ONE_PER_DAY_LIMITER_KEY =
      "APP_FOREGROUND_ONE_PER_DAY_LIMITER_KEY";

 // Added by GZ
    private static final String APP_FOREGROUND_ONE_PER_MINUTES_LIMITER_KEY =
      "APP_FOREGROUND_ONE_PER_MINUTES_LIMITER_KEY";

  //here gz
  @Provides
  @AppForeground
  public RateLimit providesAppForegroundRateLimit() {
    return RateLimit.builder()
        .setLimit(1)
        .setLimiterKey(APP_FOREGROUND_ONE_PER_MINUTES_LIMITER_KEY)
        .setTimeToLiveMillis(TimeUnit.MINUTES.toMillis(1))
        .build();
  }
}
