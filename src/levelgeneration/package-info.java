package levelgeneration;

/**This package contains everything necessary to generate a level, either from a file or generating a random level.
 * <p>
 * The Level Generators, Readers and Writers should utilize the Enums for Blocks and Sprites, to help regulate and standardize the
 * Blocks and Sprites present in the game's code. This should also help the modularity of the level creation whenever new Sprites or
 * Blocks are added.
 * <p>
 * To add Blocks or Sprites to the Level Generation, Reading and Writing, add an entry to the respective Enum that includes the Name
 * that will be used to read from a level file as well as display in generation. MAke sure that the name of the Block or Sprite match the 
 * name of the Enum entry. 
 *
 * @author Brady O'Leary
 *
 */