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

package com.google.firebase.inappmessaging.model;

/** @hide */
public class TriggeredInAppMessage {
  private final InAppMessage inAppMessage;
  private final String triggeringEvent;

  public TriggeredInAppMessage(InAppMessage inAppMessage, String triggeringEvent) {
    this.inAppMessage = inAppMessage;
    this.triggeringEvent = triggeringEvent;
  }

  public InAppMessage getInAppMessage() {
    return inAppMessage;
  }

  public String getTriggeringEvent() {
    return triggeringEvent;
  }
}
