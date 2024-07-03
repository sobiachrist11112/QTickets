package com.production.qtickets.interfaces;

public interface BasePresenter<T extends BaseView> {

  void attachView(T view);

  void detach();
}
