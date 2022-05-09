from point import Point


class Player:

    def __init__(self):
        self.head = Point(2, 3)
        self.tail = []
        self.direction = 0

    def set_direction(self, new_direction):
        self.direction = new_direction

    def move(self):
        self.head.move(self.direction)

    def append_tail(self):
        self.tail.append(self.direction)
