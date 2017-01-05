/**
 * 
 */
package unicon.matthews.dataloader.processors;

import java.util.Collection;

import unicon.matthews.entity.ClassMapping;
import unicon.matthews.oneroster.Class;

/**
 * @author ggilbert
 *
 */
public interface ClassProcessor {
  void post(Class klass);
  void post(Collection<Class> klasses);
  void post(ClassMapping klassMapping);
}
