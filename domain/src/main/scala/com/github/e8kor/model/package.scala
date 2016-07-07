package com.github.e8kor

package object model {

  type Record = (String, Iterable[(Country, Iterable[(Airport, Iterable[Runaway])])])

}
