import time

from point import Point


class SnakeGame:

    def __init__(self):
        self.head1 = Point(2, 3)
        self.head2 = Point(4, 5)
        self.tail1 = []
        self.tail2 = []
        self.d1 = 0
        self.d2 = 2
        self.food = Point(9, 1)
        self.function_matrix = [
            [self.set_d1], [self.set_d2]
        ]

    def update_state(self):
        self.head1.move(self.d1)
        self.head2.move(self.d2)

        if self.head1 == self.food:
            self.tail1.append(self.d1)

        if self.head2 == self.food:
            self.tail2.append(self.d2)

    def set_d1(self, new_d1):
        self.d1 = new_d1

    def set_d2(self, new_d2):
        self.d2 = new_d2

    def get_state(self):
        return {
            "head1": {
                "x": str(self.head1.x),
                "y": str(self.head1.y),
                "tail": str(self.tail1)
            },
            "head2": {
                "x": str(self.head2.x),
                "y": str(self.head2.y),
                "tail": str(self.tail2)
            }
        }

    def operation_from_function_matrix(self, user, function, param):
        self.function_matrix[user][function](param)
