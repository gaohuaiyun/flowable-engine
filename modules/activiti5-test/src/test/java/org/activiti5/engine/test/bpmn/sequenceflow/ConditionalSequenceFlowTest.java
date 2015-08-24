/* Licensed under the Apache License, Version 2.0 (the "License");
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

package org.activiti5.engine.test.bpmn.sequenceflow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti5.engine.impl.test.PluggableActivitiTestCase;
import org.activiti5.engine.impl.util.CollectionUtil;


/**
 * @author Joram Barrez
 */
public class ConditionalSequenceFlowTest extends PluggableActivitiTestCase {
  
  @Deployment
  public void testUelExpression() {
    Map<String, Object> variables = CollectionUtil.singletonMap("input", "right");
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("condSeqFlowUelExpr", variables);
    
    Task task = taskService
      .createTaskQuery()
      .processInstanceId(pi.getId())
      .singleResult();
    
    assertEquals("task right", task.getName());
  }
  
  @Deployment
  public void testSkipExpression() {
    Map<String, Object> variables = new HashMap<String,Object>();
    variables.put("input", "right");
    variables.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);
    variables.put("skipLeft", true);
    variables.put("skipRight", false);
    ProcessInstance pi = runtimeService.startProcessInstanceByKey("testSkipExpression", variables);
    
    Task task = taskService
      .createTaskQuery()
      .processInstanceId(pi.getId())
      .singleResult();
    
    assertEquals("task left", task.getName());
  }
}