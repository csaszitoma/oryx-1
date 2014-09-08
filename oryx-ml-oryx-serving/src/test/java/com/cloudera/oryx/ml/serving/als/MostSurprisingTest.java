/*
 * Copyright (c) 2014, Cloudera, Inc. and Intel Corp. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */

package com.cloudera.oryx.ml.serving.als;

import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.cloudera.oryx.ml.serving.IDValue;

public final class MostSurprisingTest extends AbstractALSServingTest {

  @Test(expected = BadRequestException.class)
  public void testNoArg() {
    target("/mostSurprising").request().get(String.class);
  }

  @Test
  public void testMostSurprisingJson() {
    List<IDValue> recs = target("mostSurprising/U0").request()
        .accept(MediaType.APPLICATION_JSON_TYPE).get(LIST_ID_VALUE_TYPE);
    Assert.assertEquals(3, recs.size());
    Assert.assertEquals("I0", recs.get(0).getID());
    Assert.assertEquals(0.6123868244413511, recs.get(0).getValue(), DOUBLE_EPSILON);
  }

  @Test
  public void testHowMany() {
    testHowMany("/mostSurprising/U4", 10, 6);
    testHowMany("/mostSurprising/U4", 9, 6);
    testHowMany("/mostSurprising/U4", 5, 5);
  }

  @Test
  public void testOffset() {
    testOffset("/mostSurprising/U4", 10, 0, 6);
    testOffset("/mostSurprising/U4", 9, 3, 3);
    testOffset("/mostSurprising/U4", 5, 6, 0);
  }

}
