/**
 * 
 */
package unicon.matthews.dataloader.processors;

import java.util.Collection;

import unicon.matthews.caliper.Event;

/**
 * @author ggilbert
 *
 */
public interface EventProcessor {
  void post(Event event);
  void post(Collection<Event> events);
}
