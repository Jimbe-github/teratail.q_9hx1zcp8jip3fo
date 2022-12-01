package com.teratail.q_9hx1zcp8jip3fo;

import java.io.Serializable;

public class Data implements Serializable {
  final long id;
  final String text1, text2;

  Data(long id, String text1, String text2) {
    this.id = id;
    this.text1 = text1;
    this.text2 = text2;
  }
}
