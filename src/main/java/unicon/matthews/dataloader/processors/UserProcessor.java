/**
 * 
 */
package unicon.matthews.dataloader.processors;

import java.util.Collection;

import unicon.matthews.entity.UserMapping;
import unicon.matthews.oneroster.User;

/**
 * @author ggilbert
 *
 */
public interface UserProcessor {
  void post(User user);
  void post(Collection<User> users);
  void post(UserMapping userMapping);
}
