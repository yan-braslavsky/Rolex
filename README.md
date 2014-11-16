Watch-View
==========

This is an implementation for another android custom view.The view is a simple analog watch. The code can help you understand how a custom view can be created.Or you can jsut use this custom view in your project as it is or with some adaptations.

### Purpose of this project
Here I want to show you how a relatively complex custom view can be implemented.
I tried to keep the code as documented as possible , so you could understand it easily.
If you find the Watch-View cool you are welcome to use it in your own project.
If you find the Watch-View cool and want to appreciate my work , just post something on my [Facebook wall](https://www.facebook.com/yan.braslavskiy) .Please link this repository in the post.

### Example Description

In this example you can :
* Move each arrow with a finger to set it to desired position.
* Rotate a winding wheel to make the watch ticking.
* Click on the scale button to see how WatchView us scaled down.
* While WatchView is scaled you still can touch and manipulated as expected.


### Possible real application usages.
*  You want to give your user a possibility to adjust a time in an authentic way.
*  You want to present user with a time in an authentic way.

### Limitations
 * As I wanted to keep the view multi purpose , it doesn't contain too much logic. So if you want to make it ticking or arrows follow one another , you should be able to do so using the provided listeners.Like I did in the example.
 * Currently watch view should have a fixed aspect ration of "1" (width = height) , otherwise visual distortions will occur.I didn't want to handle special cases , because this handling can depend on your specific needs.You can handle it yourself :)

### Performance
I didn't run any stress tests on the watch view , but it shouldn't be very performance consuming. I tried to keep the code readable and decoupled rather than optimized for the best performance. So you are sure to find some areas to improve.With that being said , it still runs pretty good :)

### Screen Shot
 ![ScreenShot](https://raw.githubusercontent.com/ivelius/Watch-View/master/app/src/main/res/watch.png)


License
----
**Free Software, Hell Yeah!**
