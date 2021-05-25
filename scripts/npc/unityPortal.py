from net.swordie.ms.enums import DimensionalPortalType

return_field = sm.getFieldID()
current = sm.getFieldID()
response = sm.sendAskSlideMenu(0)
mapID = DimensionalPortalType.getByVal(response).getMapID()

if mapID != 0 and sm.getFieldID() == current:
    sm.setReturnField(return_field)
    sm.setReturnPortal(0)
    sm.warp(mapID)
