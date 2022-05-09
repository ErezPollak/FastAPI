from point import Point


class Player:

    def __init__(self, initial_point: Point):
        self.head = initial_point
        self.tail = []
        self.direction = 0

    def set_direction(self, new_direction):
        self.direction = new_direction

    def move(self):
        self.tail.append(self.direction)
        self.tail.pop()
        self.head.move(self.direction)

    def append_tail(self):
        self.tail.append(self.direction)
