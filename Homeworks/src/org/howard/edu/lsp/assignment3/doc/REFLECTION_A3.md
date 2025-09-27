#Reflection
In both Assignment 2 and Assignment 3, I was tasked with developing an ETL pipeline in Java to transform a CSV file. However, the approach in Assignment 3 was significantly more structured and modular, adhering to object-oriented principles.

##Design Differences
In Assignment 2, I created a straightforward procedural ETL pipeline where the extraction, transformation, and loading steps were all done sequentially in the same file. The logic was centralized, and everything was tightly coupled, making it harder to extend or modify specific parts of the pipeline.

For Assignment 3, the task was to break down the pipeline into multiple classes, each responsible for a specific part of the process. This allowed for better encapsulation and modularity. I split the responsibilities into different classes like Extractor, Transformer, Loader, and Row, which helped in making the code more maintainable and flexible. Each class now has a clear, well-defined role, which is a key advantage of object-oriented design.

##Object-Orientedness

###What makes it more object-oriented?
Assignment 3 redesigns the pipeline using object-oriented principles, which allows for greater flexibility, maintainability, and scalability. The core functionality is broken down into separate classes with distinct responsibilities:

Extractor: Responsible for reading the CSV and extracting rows.

Transformer: Manages a series of transformation rules and applies them to each row.

Loader: Responsible for writing the transformed rows back into a CSV file.

Row: Represents each individual data record (row) as a map, making it easier to modify or extend.

###Object Oriented Concepts Used
The transformations are now encapsulated in separate classes that implement the TransformationRule interface (e.g., TitleCaseRule, PriceNormalizeRule, PriceRangeRule), which can be reused or replaced easily without modifying the core pipeline. Each rule encapsulates a single piece of transformation logic, and the Transformer class applies these rules in a flexible, modular fashion.

Encapsulation: Each class is responsible for a single piece of the ETL pipeline, and the logic for transformations is contained within specific rule classes. The Transformer class holds the transformation rules, and the Row class encapsulates the data for each record, ensuring that the logic for data manipulation is isolated and reusable.

Inheritance & Polymorphism: The transformation rules, like TitleCaseRule, PriceNormalizeRule, and PriceRangeRule, all implement the TransformationRule interface. This allowed me to treat each transformation in a uniform way and add new transformations without changing the core pipeline.

Modularity: By breaking down the tasks into smaller, manageable classes, I could easily modify or add new transformations. For example, adding the PriceRangeRule was simple because it didn’t require altering the core pipeline—just adding the rule to the Transformer was enough.

##Testing and Validation
To ensure that the object-oriented design still produced the same results as Assignment 2, I added print statements throughout the transformation process. I printed the data after extraction, after each transformation, and before writing the output to the CSV. This allowed me to visually verify that the data was being transformed correctly at each step.

In Assignment 2, I simply processed the data in one go, while in Assignment 3, I had to ensure that the transformations were applied in the correct order and that the data flowed seamlessly between the classes. This made it a bit more challenging, but also more rewarding, as I could easily debug and isolate issues in specific transformation rules.

##Challenges and Improvements
One of the main challenges was ensuring that the transformation rules were applied correctly in the right order. In Assignment 2, this was implicitly managed, but in Assignment 3, I had to carefully structure the rules in the Transformer class. I also had to define a new PriceRangeRule to match the behavior from Assignment 2, which required me to think about the design of transformation rules in a more modular way.

#Conclusion
Overall, the move to an object-oriented approach in Assignment 3 made the ETL pipeline more scalable and maintainable. It was a good exercise in applying OOP principles like encapsulation, modularity, and polymorphism. The transformation logic is now more organized and can easily be extended or modified in the future. I believe the object-oriented design not only improved the readability of the code but also made it easier to debug and verify correctness.

In comparison, while Assignment 2 worked well for simple tasks, Assignment 3’s object-oriented structure has better long-term benefits, especially when handling more complex transformations or adding new features.