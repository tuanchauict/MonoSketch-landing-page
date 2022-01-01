package io.monosketch.web

object GraphRes {
    private val introGraphs = framesOf(
        "client-server-db",
        """
┌───────────────────────────────────────────────┐
│                                               │
│    Client           Server         Database   │
│                                     Server    │
│      █    message #1   █               █      │
│      █─ ─── ─── ─── ──▶█   update #1   █      │
│      █                 █─ ─── ─── ─── ▶█      │
│      █                 █               █      │
│      █                 █    OK #1      █      │
│      █      DONE #1    █◀── ─── ─── ───█      │
│      █◀── ─── ─── ─── ─█               █      │
│      █                 █               █      │
│                                               │
└───────────────────────────────────────────────┘
        """.trim(),
        """
┌───────────────────────────────────────────────┐
│                                               │
│    Client           Server         Database   │
│                                     Server    │
│      █    message #1   █               █      │
│      █── ─── ─── ─── ─▶█   update #1   █      │
│      █                 █── ─── ─── ───▶█      │
│      █                 █               █      │
│      █                 █    OK #1      █      │
│      █      DONE #1    █◀─ ─── ─── ─── █      │
│      █◀─ ─── ─── ─── ──█               █      │
│      █                 █               █      │
│                                               │
└───────────────────────────────────────────────┘
        """.trim(),
        """
┌───────────────────────────────────────────────┐
│                                               │
│    Client           Server         Database   │
│                                     Server    │
│      █    message #1   █               █      │
│      █─── ─── ─── ─── ▶█   update #1   █      │
│      █                 █─── ─── ─── ──▶█      │
│      █                 █               █      │
│      █                 █    OK #1      █      │
│      █      DONE #1    █◀ ─── ─── ─── ─█      │
│      █◀ ─── ─── ─── ───█               █      │
│      █                 █               █      │
│                                               │
└───────────────────────────────────────────────┘
        """.trim(),
        """
┌───────────────────────────────────────────────┐
│                                               │
│    Client           Server         Database   │
│                                     Server    │
│      █    message #1   █               █      │
│      █ ─── ─── ─── ───▶█   update #1   █      │
│      █                 █ ─── ─── ─── ─▶█      │
│      █                 █               █      │
│      █                 █    OK #1      █      │
│      █      DONE #1    █◀─── ─── ─── ──█      │
│      █◀─── ─── ─── ─── █               █      │
│      █                 █               █      │
│                                               │
└───────────────────────────────────────────────┘
        """.trim()
    )

    private val featureGraphs = framesOf(
        "feature-tool-rect",
        """▫             
              
              
              
              """,
        """┌─┐           
└─┘           
              
              
              """,
        """┌──────┐      
│      │      
└──────┘      
              
              """,
        """┌──────────┐  
│          │  
│          │  
└──────────┘  
              """,
        """┌────────────┐
│            │
│            │
│            │
└────────────┘"""
    ) + framesOf(
        "feature-tool-text",
        """

│             

""",
        """

H│            

""",
        """

He│           

""",
        """

Hel│          

""",
        """

Hell│         

""",
        """

Hello│        

""",
        """

Hello │       

""",
        """

Hello W│      

""",
        """

Hello Wo│     

""",
        """

Hello Wor│    

""",
        """

Hello Worl│   

""",
        """

Hello World│  

""",
        """

Hello World!!│

""",
        """

Hello World!!!

"""
    ) + framesOf(
        "feature-tool-line",
        """              
              
              
              
 ■            """,
        """              
              
              
              
 ■──▶         """,
        """              
              
              
              
 ■───────▶    """,
        """              
              
              
              
 ■──────────▶ """,
        """              
              
              
            ▲ 
 ■──────────┘ """,
        """              
              
            ▲ 
            │ 
 ■──────────┘ """,
        """            ▲ 
            │ 
            │ 
            │ 
 ■──────────┘ """
    )

    private val graphs = (introGraphs + featureGraphs).toMap()

    fun get(key: String): String? = graphs[key]

    private fun framesOf(prefix: String, vararg frames: String): List<Pair<String, String>> =
        frames.mapIndexed { index, s -> "$prefix#${index + 1}" to s }
}
