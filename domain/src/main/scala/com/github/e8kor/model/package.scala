package com.github.e8kor

package object model {

  type RecordWithKey = (String, Iterable[(Country, Iterable[(Airport, Iterable[Runaway])])])

  type Record = (Country, Iterable[(Airport, Iterable[Runaway])])

}
