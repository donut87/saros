---
title: Install Saros for Eclipse
---

# {{ page.title }}
{:.no_toc}

{% include toc.html %}

## Prerequisites

*   Saros requires **Java SE 6** or higher
    *   [Java download](http://www.java.com/en/)
*   Saros is developed and tested using **Eclipse 3.6 and 3.7**, but it
    should work on any newer version without problems.
    *   [Download Eclipse](http://www.eclipse.org/downloads/)
*   Saros ought to run on all platforms that can execute Eclipse
    cleanly. 
*   If you want to try Saros, you may use our public XMPP/Jabber server
    `saros-con.imp.fu-berlin.de`. However, if you want to use Saros for
    commercial purposes, we recommend [**setting up your own XMPP
    server**](setup-xmpp.md) for maximum control over all
    exchanged data.
*   The whiteboard feature is based on the Eclipse Graphical Editing
    Framework (GEF) 3.6 or higher
    *   It is **part of the Eclipse RCP and Eclipse EE** downloads
    *   Using the Saros update site (see beyond), GEF should be
        **automatically installed**.
    *   You can also install it using the [GEF update
        site](http://www.eclipse.org/gef/updates/index.php)
    *   Or [download as
        dropin](http://www.eclipse.org/gef/downloads/index.php)


## Installation Steps

### Via Eclipse Marketplace

You can install Saros from the [Eclipse Marketplace
website](http://marketplace.eclipse.org/content/saros-distributed-collaborative-editing-and-pair-programming-0)
or from within Eclipse:

1.  Open Eclipse
2.  Open the marketplace: *Menu &gt; Help &gt; Eclipse Marketplace...*
3.  Search for "Saros"
4.  Click on the "Install" button

### Via Update Site

**The Eclipse plugin update site is:
<http://dpp.sourceforge.net/update>**

1.  Open Eclipse
2.  Open the software updates manager: *Menu &gt; Help &gt; Install
    New Software...*
3.  In the *Available Software* wizard select *Add*
4.  Enter `http://dpp.sourceforge.net/update` in the Location field;
    press OK
    -   You may also use our secondary update site:
        `https://get-saros.herokuapp.com/eclipse/`

5.  Choose the plugin in the the list of available software and select
    it
6.  In the next window select *Next *to confirm installation
7.  Accept the license agreement and select *Finish*
8.  Restart Eclipse

### As Dropin

1.  Download the [latest *saros-dropin-\*.zip*
    from Sourceforge](http://sourceforge.net/projects/dpp/files/latest/download?source=files).
2.  If not already available, download the [Graphical Editing
    Framework](http://www.eclipse.org/gef/downloads/index.php)
3.  Uncompress the archives into the dropins folder of your
    Eclipse installation.
4.  Restart Eclipse.

## Good to know

*   The last version tested with Java SE 5 was [DPP
    10.6.25.r2236](https://sourceforge.net/projects/dpp/files/Saros/DPP%2010.6.25.r2236/)
*   Saros is by-and-large compatible with almost all other
    Eclipse plug-ins.
*   In our tests, we mainly use Windows (7 and 10), Linux (Ubuntu) and
    Mac OS X (10.6). In Mac OS X 10.6 you will need Eclipse 3.5 64bit
    version
*   Please note that Saros requires higher bandwidth and lower latency
    than normal chatting, so public chat servers might be too slow for
    smooth operation of Saros.
