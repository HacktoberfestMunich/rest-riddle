package de.pixel.restriddle.challenges

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RegularChallenges {

    private val tree = createTree()

    @GetMapping("/tree")
    fun getTree(): Tree {
        return tree
    }

    @GetMapping("/tree/branch/{branch}")
    fun getBranch(@PathVariable("branch") branchId: Int): Branch = tree.branches[branchId]

    @GetMapping("/tree/leaf/{leaf}")
    fun getLeaf(@PathVariable("leaf") leafId: Int): Leaf = tree.leafs[leafId]

    @GetMapping("/tree/branch/{branch}/leaf/{leaf}")
    fun getBranchLeaf(@PathVariable("branch") branchId: Int, @PathVariable("leaf") leafId: Int): Leaf = tree.branches[branchId].leafs[leafId]

    @GetMapping("/tree/branch/{branch}/apple/{apple}")
    fun getBranchApples(@PathVariable("branch") branchId: Int, @PathVariable("apple") appleId: Int): Apple = tree.branches[branchId].apples[appleId]

    @GetMapping("/tree/branch/{branch}/apple/{apple}/worms/{wormname}")
    fun getAppleWorms(@PathVariable("branch") branchId: Int, @PathVariable("apple") appleId: Int, @PathVariable("wormname") wormname: String): Worm =
        tree.branches[branchId].apples[appleId].worms.first { it.name == wormname }

    private fun createTree(): Tree {
        val eddie = Worm("Eddie")
        val markus = Worm("Markus")
        val tobias = Worm("Tobias")
        val unknown = Worm("Mr. Unworm")

        val apple1 = Apple(listOf(eddie))
        val apple2 = Apple(listOf())
        val apple3 = Apple(listOf(markus, tobias))
        val apple4 = Apple(listOf(unknown))

        val branch1 = Branch(listOf(), 3, listOf(Leaf(12)))
        val branch2 = Branch(listOf(apple2), 3, listOf(Leaf(7), Leaf(59)))
        val branch3 = Branch(listOf(), 3, listOf(Leaf(8)))
        val branch4 = Branch(listOf(apple4), 3, listOf(Leaf(24)))
        val branch5 = Branch(listOf(apple3), 3, listOf(Leaf(5), Leaf(92)))
        val branch6 = Branch(listOf(apple1, apple2), 3, listOf(Leaf(1)))

        return Tree(listOf(branch1, branch2, branch3, branch3, branch4, branch5, branch6), listOf(Leaf(10), Leaf(18), Leaf(35)))
    }
}

data class Tree(val branches: List<Branch>, val leafs: List<Leaf>)

data class Branch(val apples: List<Apple>, val ends: Int, val leafs: List<Leaf>)

data class Leaf(val amount: Int)

data class Apple(val worms: List<Worm>)

data class Worm(val name: String)
