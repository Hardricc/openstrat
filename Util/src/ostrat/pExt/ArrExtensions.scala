package ostrat
package pExt

class ArrExtensions[A](thisArr: Arr[A])
{
  def optAppend(optElems: Option[Arr[A]]): Arr[A] = optElems.fold[Arr[A]](thisArr)(bs => thisArr ++ bs)

  /** Concatenates Arr if Some. Returns original Arr if operand is None. */
  def optConcat[B >: A](optElems: Option[Arr[B]]): Arr[B] = optElems.fold[Arr[B]](thisArr)(bs => thisArr ++ bs)
}