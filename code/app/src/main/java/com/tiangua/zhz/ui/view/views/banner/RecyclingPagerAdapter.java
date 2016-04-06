package com.tiangua.zhz.ui.view.views.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * A {@link PagerAdapter} which behaves like an {@link android.widget.Adapter} with view types and
 * view recycling.
 */
public abstract class RecyclingPagerAdapter extends PagerAdapter {
  static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;

  private final RecycleBin recycleBin;

  public RecyclingPagerAdapter() {
    this(new RecycleBin());
  }

  RecyclingPagerAdapter(RecycleBin recycleBin) {
    this.recycleBin = recycleBin;
    recycleBin.setViewTypeCount(getViewTypeCount());
  }

  @Override public void notifyDataSetChanged() {
    recycleBin.scrapActiveViews();
    super.notifyDataSetChanged();
  }

  @Override public final Object instantiateItem(ViewGroup container, int position) {
    int viewType = getItemViewType(position);
    View view = null;
    if (viewType != IGNORE_ITEM_VIEW_TYPE) {
      view = recycleBin.getScrapView(position, viewType);
    }
    view = getView(position, view, container);
    container.addView(view);
    return view;
  }

  @Override public final void destroyItem(ViewGroup container, int position, Object object) {
    View view = (View) object;
    container.removeView(view);
    int viewType = getItemViewType(position);
    if (viewType != IGNORE_ITEM_VIEW_TYPE) {
      recycleBin.addScrapView(view, position, viewType);
    }
  }

  @Override public final boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  /**
   * <p>
   * Returns the number of types of Views that will be created by
   * {@link #getView}. Each type represents a set of views that can be
   * converted in {@link #getView}. If the adapter always returns the same
   * type of View for all items, this method should return 1.
   * </p>
   * <p>
   * This method will only be called when when the adapter is set on the
   * the {@link AdapterView}.
   * </p>
   *
   * @return The number of types of Views that will be created by this adapter
   */
  public int getViewTypeCount() {
    return 1;
  }

  /**
   * Get the type of View that will be created by {@link #getView} for the specified item.
   *
   * @param position The position of the item within the adapter's data set whose view type we
   *        want.
   * @return An integer representing the type of View. Two views should share the same type if one
   *         can be converted to the other in {@link #getView}. Note: Integers must be in the
   *         range 0 to {@link #getViewTypeCount} - 1. {@link #IGNORE_ITEM_VIEW_TYPE} can
   *         also be returned.
   * @see #IGNORE_ITEM_VIEW_TYPE
   */
  @SuppressWarnings("UnusedParameters") // Argument potentially used by subclasses.
  public int getItemViewType(int position) {
    return 0;
  }


  public abstract View getView(int position, View convertView, ViewGroup container);
}
