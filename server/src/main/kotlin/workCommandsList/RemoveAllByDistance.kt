package workCommandsList

import ShaBuilder
import dataSet.Route
import dataSet.RouteComporator
import java.util.*

/**
 * Class RemoveAllByDistance. Delete all objects with the given distance.
 *
 * @author jutsoNNN
 * @since 1.0.0
 */
class RemoveAllByDistance: Command() {

    /**
     * execute method. Remove all objects with distance in parametrs
     *
     * @param getArgs arguments
     */
    override fun execute(getArgs: MutableList<Any>, login:String, uniqueToken:String){

        val checkDistance = (getArgs[0] as Double).toLong()

        val collection = PriorityQueue<Route>(RouteComporator())
        collection.addAll(workWithCollection.getCollection())
        val hashSHA = ShaBuilder()
        val owner = serverModule.availableTokens[hashSHA.toSha(login)].toString()

        if (collection.size == 0){
            workWithResultModule.setMessages("Empty collection")
        }else if(collection.size == 1){
            val checkObject = collection.peek()
            if (checkObject.distance == checkDistance && checkObject.owner == owner){
                workWithCollection.clearCollection()
                dbModule.deleteRoute(checkObject.id)
                workWithResultModule.setMessages("Cleared")
            }else{
                workWithResultModule.setMessages("No distance | Not your element")
                //workWithResultModule.setMessages("notYou")
            }
        }else{
            workWithCollection.clearCollection()
            var fl = false
            for (i in 0..collection.size - 1){
                val checkObject = collection.peek()
                if (checkObject.distance == checkDistance && checkObject.owner == owner){
                    dbModule.deleteRoute(checkObject.id)
                    collection.poll()
                    workWithResultModule.setMessages("Cleared")
                    fl = true
                }else{
                    workWithCollection.addElementToCollection(collection.peek())
                    collection.poll()
                }
            }
            if (!fl){
                workWithResultModule.setMessages("No distance | Not your element")
            }
        }
        workWithResultModule.setUniqueKey(uniqueToken)

        //serverModule.serverSender(workWithResultModule.getResultModule())
        serverModule.queueExeSen.put(workWithResultModule.getResultModule())
        workWithResultModule.clear()
    }
}