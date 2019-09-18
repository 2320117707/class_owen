package com.leyou;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class LuceneQuery {

    @Test
    public void testQueryIndex() throws Exception{
//        1、指定索引文件存储的位置  D:\class103\index
        Directory directory = FSDirectory.open(new File("D:\\class103\\index"));
//       2、 创建一个用来读取索引的对象 indexReader
        IndexReader indexReader = DirectoryReader.open(directory);
//       3、 创建一个用来查询索引的对象 IndexSearcher
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        使用term查询：指定查询的域名和关键字
//        Query query = new TermQuery(new Term("companyName","凌宇"));
//        查询所有
//        Query query = new MatchAllDocsQuery();

//        Wildcard通配符查询 like '%XXX%'
//        Query query = new WildcardQuery(new Term("companyName","*宇*"));
//        模糊查询 容错查询
//        Query query = new FuzzyQuery(new Term("companyName","另宇"));
//        区间查询
//        Query query = NumericRangeQuery.newIntRange("salary",10000,20000,true,true);
//        组合查询
      /*  BooleanQuery query = new BooleanQuery();
//        名称中带“宇”字的 并且工资范围是10000到20000之间
        Query query1 = new WildcardQuery(new Term("companyName", "*宇*"));
        Query query2 = NumericRangeQuery.newIntRange("salary",100000,200000,true,true);
        query.add(query1, BooleanClause.Occur.MUST);  //Occur.MUST 一定要符合
        query.add(query2, BooleanClause.Occur.MUST);*/

//        Query query = new TermQuery(new Term("companyName","北京东进航空科技股份有限公司"));

//        分词后查询
        QueryParser parser = new QueryParser("companyName",new IKAnalyzer());
        Query query = parser.parse("北京东进航空科技股份有限公司");

//        指定多个域名分词查询
//        QueryParser parser = new MultiFieldQueryParser(new String[]{"companyName","companyAddr"},new IKAnalyzer());
//        Query query = parser.parse("航空科技");
//        北京
//                东进
//        航空
//                科技
//        股份
//                有限公司


        TopDocs topDocs = indexSearcher.search(query, 100);//第二个参数：最多显示多少条数据
        int totalHits = topDocs.totalHits;  //查询的总数量
        System.out.println("符合条件的总数:"+totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs; //获取命中的文档 存储的是文档的id
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docID = scoreDoc.doc;
//            根据id查询文档
            Document document = indexSearcher.doc(docID);
            System.out.println( "id:"+ document.get("id"));
            System.out.println( "companyName:"+ document.get("companyName"));
            System.out.println( "companyAddr:"+ document.get("companyAddr"));
            System.out.println( "salary:"+ document.get("salary"));
            System.out.println( "url:"+ document.get("url"));
            System.out.println("**************************************************************");
        }
    }



    @Test
    public void testAnalyzer() throws Exception{
//        Analyzer analyzer = new CJKAnalyzer();
//        Analyzer analyzer = new SmartChineseAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
//        英文来说：1、安装空格拆分
//                  2、去除标点符号
//                  3、把所有的大写转成小写
//                  4、去除停用词 （不会被用来搜索的词）  a and Or 之类
        String text="Lucene北京时代凌宇科技股份有限公司是他妈的个什么的呢";
//        String text="The Spring Framework provides a comprehensive programming and configuration model.";
        TokenStream tokenStream = analyzer.tokenStream("test", text);
//        设置指针引用
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//        指针复位
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute);
        }
    }
}
