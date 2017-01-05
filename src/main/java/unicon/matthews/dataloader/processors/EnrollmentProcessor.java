/**
 * 
 */
package unicon.matthews.dataloader.processors;

import java.util.Collection;

import unicon.matthews.oneroster.Enrollment;

/**
 * @author ggilbert
 *
 */
public interface EnrollmentProcessor {
  void post(Enrollment enrollment);
  void post(Collection<Enrollment> enrollments);
}
