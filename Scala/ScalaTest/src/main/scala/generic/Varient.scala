package main.scala.generic

/**
 * @author kwjang
 */
class Varient {
  class Dog
  class Puppy extends Dog
  
  //contravariance
  trait PutBox[-T]{
    def put(value: T): Unit = ???
  }
  
  //covariance
  trait GetBox[+T]{
    def get: T = ???
  }
  
  trait PutGetBox[A] extends PutBox[A] with GetBox[A]
  
  object Boxes{
    def putPuppy(box: PutBox[Puppy]): Unit = box.put(new Puppy)
    def getDog(box: GetBox[Dog]): Dog = box.get
    
    val dogPutBox = new PutBox[Dog] {}
    val dogGetBox = new GetBox[Dog] {}
    val puppyPutBox = new PutBox[Puppy] {}
    val puppyGetBox = new GetBox[Puppy] {}
    
    putPuppy(puppyPutBox)
    putPuppy(dogPutBox)
    getDog(dogGetBox)
    getDog(puppyGetBox)
  }
}