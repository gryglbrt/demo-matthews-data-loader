/**
 * 
 */
package unicon.matthews.dataloader.processors;

import java.util.Collection;

import unicon.matthews.oneroster.LineItem;

/**
 * @author ggilbert
 *
 */
public interface LineItemProcessor {
  void post(LineItem lineItem);
  void post(Collection<LineItem> lineItems);
}
