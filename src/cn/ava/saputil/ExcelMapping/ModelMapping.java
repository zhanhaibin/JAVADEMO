package cn.ava.saputil.ExcelMapping;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import com.sun.org.apache.xpath.internal.operations.Variable;

import cn.ava.publics.util.EmptyUtil;

public class ModelMapping {
	   private ObjectArea _CurrentArea;
       public ObjectArea CurrentArea()       {
             return _CurrentArea;  
       }

//       private System.Data.DataTable _Table;
//
//       public System.Data.DataTable Table
//       {
//           get { return _Table; }
//           set { _Table = value; }
//       }

       private int _RowOffset = 0;
       public int RowOffset()       {
             return _RowOffset;  
       }
       public  void SetRowOffset(int RowOffset) {
    	   this._RowOffset = RowOffset;
       }
	 
       public void GetModelDomain(IModelDomain domain)
       {
           SetRowOffset(0);
           ITable table = null;
           IField field = null;
//           IValidValue valid = null;
//           IBusinessObject biz = null;
//           IChildTable child = null;
//           if (domain == null)
//           {
//               return;
//           }
//           if (!(Table.Rows.Count > 0))
//           {
//               return;
//           }
//           if (!(Table.Columns.Count > 9))
//           {
//               return;
//           }
//           while (RowOffset < Table.Rows.Count)
//           {
//               System.Data.DataRow row = Table.Rows[RowOffset];
//               switch (GetAreaType(row))
//               {
//                   case ObjectAreaType.NotDefind:
//
//                       break;
//                   case ObjectAreaType.TableArea:
//                       table = domain.Tables.Add();
//                       SetModelValues(Table, RowOffset, table);
//                       break;
//                   case ObjectAreaType.FieldTitleArea:
//
//                       break;
//                   case ObjectAreaType.FieldArea:
//                       if (table != null)
//                       {
//                           field = table.Fields.Add();
//                           SetModelValues(Table, RowOffset, field);
//                       }
//                       break;
//                   case ObjectAreaType.ValidValueArea:
//                       if (field != null)
//                       {
//                           valid = field.ValidValues.Add();
//                           SetModelValues(Table, RowOffset, valid);
//                       }
//                       break;
//                   case ObjectAreaType.BusinessObjectArea:
//                       if (domain != null)
//                       {
//                           biz = domain.BusinessObjects.Add();
//                           SetModelValues(Table, RowOffset, biz);
//                       }
//                       break;
//                   case ObjectAreaType.BusinessObjectChildTableArea:
//                       if (biz != null)
//                       {
//                           RowOffset += CurrentArea.RowCount;
//                           while (RowOffset < Table.Rows.Count)
//                           {
//                               //child = biz.ChildTables.Add();
//                               child = new ChildTable();
//                               SetModelValues(Table, RowOffset, child);
//                               if (!string.IsNullOrEmpty(child.TableName))
//                               {
//                                   biz.ChildTables.Add(child);
//                               }
//                               RowOffset += 1;
//                           }
//                       }
//                       break;
//                   default:
//                       break;
//               }
//               if (CurrentArea != null)
//               {
//                   RowOffset += CurrentArea.RowCount;
//               }
//               else
//               {
//                   RowOffset += 1;
//               }
//           };
       }

       private void SetModelValues(XSSFRow row, Object obj)
       {
           if (EmptyUtil.isNotEmpty(CurrentArea()))
           {
        	   for (Property item : CurrentArea().Propertys()) {
				
//                   _pInfoDic.SetModelValue(obj, item.Name(), CurrentArea().ValueMaps.toMapping(row[item.Column].ToString()));
               }
           }
       }


//       private void SetModelValues(System.Data.DataTable table, int RowOffset, object obj)
//       {
//           try
//           {
//               if (CurrentArea != null)
//               {
//                   foreach (var item in CurrentArea.Propertys)
//                   {
//                       if (item.Offset + RowOffset < table.Rows.Count)
//                       {
//                           System.Data.DataRow row = table.Rows[RowOffset + item.Offset];
//                           _pInfoDic.SetModelValue(obj, item.Name, CurrentArea.ValueMaps.toMapping(row[item.Column].ToString()));
//                       }
//
//                   }
//               }
//           }
//           catch (Exception ex)
//           {
//               throw (new Exception("错误：" + table.TableName + "/r/n" + ex.Message));
//           }
//       }

//       private ObjectAreaType GetAreaType(System.Data.DataRow row)
//       {
//           foreach (var item in _Arealist)
//           {
//               if (item.CheckAreaType(row))
//               {
//                   _CurrentArea = item;
//                   return (ObjectAreaType)Enum.Parse(typeof(ObjectAreaType), item.Name);
//               }
//           }
//           _CurrentArea = null;
//           return ObjectAreaType.NotDefind;
//       }
//
//       private PropertyInfoManager _pInfoDic = new PropertyInfoManager();
//       public void GetMappingConfig(string fileName)
//       {
//           if (System.IO.File.Exists(fileName))
//           {
//               System.Xml.XmlDocument xmlDoc = new System.Xml.XmlDocument();
//               xmlDoc.Load(fileName);
//               GetObjectAreas(xmlDoc);
//           }
//           else
//           {
//               throw new Exception(string.Format("file was not exists.[{0}]", fileName));
//           }
//       }
//       //此处匹配xml与excel
//       private List<ObjectArea> _Arealist = new List<ObjectArea>();
//       void GetObjectAreas(System.Xml.XmlDocument xmlDoc)
//       {
//           System.Xml.XmlNodeList listArea = xmlDoc.GetElementsByTagName("ObjectArea");
//           ObjectArea area;
//           Condition con;
//           Property pty;
//           ValueMap map;
//           foreach (System.Xml.XmlNode areaNode in listArea)
//           {
//               area = new ObjectArea();
//
//               _pInfoDic.GetProperties(area); // 获取节点属性
//               _pInfoDic.SetModelValue(area, areaNode); //获取节点属性的值
//
//               foreach (System.Xml.XmlNode condNode in areaNode.SelectSingleNode("Conditions"))
//               {
//                   con = new Condition();
//                   _pInfoDic.GetProperties(con);
//                   _pInfoDic.SetModelValue(con, condNode);
//                   area.Conditions.Add(con);
//               }
//               foreach (System.Xml.XmlNode ptyNode in areaNode.SelectSingleNode("Propertys"))
//               {
//                   pty = new Property();
//                   _pInfoDic.GetProperties(pty);
//                   _pInfoDic.SetModelValue(pty, ptyNode);
//                   area.Propertys.Add(pty);
//               }
//               foreach (System.Xml.XmlNode mapNode in areaNode.SelectSingleNode("ValueMaps"))
//               {
//                   map = new ValueMap();
//                   _pInfoDic.GetProperties(map);
//                   _pInfoDic.SetModelValue(map, mapNode);
//                   area.ValueMaps.Add(map);
//               }
//               _Arealist.Add(area);
//           }
//       }
//
//       public string GetMappingData(ObjectAreaType type, object data)
//       {
//           foreach (var item in this._Arealist)
//           {
//               if (!string.Equals(item.Name, type.ToString(), StringComparison.InvariantCultureIgnoreCase))
//                   continue;
//               foreach (var dItem in item.ValueMaps)
//               {
//                   if (string.Equals(data.ToString(), dItem.Target, StringComparison.InvariantCultureIgnoreCase))
//                       return dItem.Source;
//               }
//           }
//           return Convert.ToString(data);
//       }
   }
