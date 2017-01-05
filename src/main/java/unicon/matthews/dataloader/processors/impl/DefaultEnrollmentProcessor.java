package unicon.matthews.dataloader.processors.impl;

import java.util.Collection;

import unicon.matthews.dataloader.MatthewsClient;
import unicon.matthews.dataloader.processors.EnrollmentProcessor;
import unicon.matthews.oneroster.Enrollment;

public class DefaultEnrollmentProcessor implements EnrollmentProcessor {
  
  protected MatthewsClient matthewsClient;
  
  public DefaultEnrollmentProcessor(MatthewsClient matthewsClient) {
    this.matthewsClient = matthewsClient;
  }

  @Override
  public void post(Enrollment enrollment) {
    this.matthewsClient.postEnrollment(enrollment);
  }

  @Override
  public void post(Collection<Enrollment> enrollments) {
    for (Enrollment enrollment : enrollments) {
      post(enrollment);
    }
  }
}
